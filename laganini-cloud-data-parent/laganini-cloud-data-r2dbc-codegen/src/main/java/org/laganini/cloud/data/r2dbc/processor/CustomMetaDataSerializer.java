package org.laganini.cloud.data.r2dbc.processor;

import com.google.common.base.CaseFormat;
import com.querydsl.codegen.*;
import com.querydsl.codegen.utils.CodeWriter;
import com.querydsl.codegen.utils.model.Parameter;
import com.querydsl.codegen.utils.model.TypeCategory;
import com.querydsl.codegen.utils.model.Types;
import com.querydsl.sql.ColumnMetadata;
import com.querydsl.sql.codegen.MetaDataSerializer;
import com.querydsl.sql.codegen.NamingStrategy;
import com.querydsl.sql.codegen.SQLCodegenModule;
import org.springframework.data.relational.core.mapping.Column;

import javax.annotation.processing.ProcessingEnvironment;
import javax.inject.Inject;
import javax.inject.Named;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.querydsl.codegen.utils.Symbols.*;

public class CustomMetaDataSerializer extends MetaDataSerializer {

    private final ProcessingEnvironment processingEnvironment;
    private final CaseFormat            columnCaseFormat;

    @Inject
    public CustomMetaDataSerializer(
            TypeMappings typeMappings,
            NamingStrategy namingStrategy,
            ProcessingEnvironment processingEnvironment,
            CaseFormat columnCaseFormat,
            @Named(SQLCodegenModule.INNER_CLASSES_FOR_KEYS) boolean innerClassesForKeys,
            @Named(SQLCodegenModule.IMPORTS) Set<String> imports,
            @Named(SQLCodegenModule.COLUMN_COMPARATOR) Comparator<Property> columnComparator,
            @Named(SQLCodegenModule.ENTITYPATH_TYPE) Class<?> entityPathType,
            @Named(SQLCodegenModule.GENERATED_ANNOTATION_CLASS) Class<? extends Annotation> generatedAnnotationClass
    )
    {
        super(
                typeMappings,
                namingStrategy,
                innerClassesForKeys,
                imports,
                columnComparator,
                entityPathType,
                generatedAnnotationClass
        );
        this.processingEnvironment = processingEnvironment;
        this.columnCaseFormat = columnCaseFormat;
    }

    @Override
    protected void constructorsForVariables(CodeWriter writer, EntityType model) throws IOException {
        String localName   = writer.getRawName(model);
        String genericName = writer.getGenericName(true, model);
//        super.constructorsForVariables(writer, model);

        boolean stringOrBoolean = model.getOriginalCategory() == TypeCategory.STRING
                || model.getOriginalCategory() == TypeCategory.BOOLEAN;
        boolean hasEntityFields  = model.hasEntityFields() || superTypeHasEntityFields(model);
        String  thisOrSuper      = hasEntityFields ? THIS : SUPER;
        String  additionalParams = hasEntityFields ? "" : getAdditionalConstructorParameter(model);

        if (!localName.equals(genericName)) {
            suppressAllWarnings(writer);
        }
        writer.beginConstructor(new Parameter("variable", Types.STRING));
        if (stringOrBoolean) {
            writer.line(thisOrSuper, "(forVariable(variable)", additionalParams, ");");
        } else {
            writer.line(
                    thisOrSuper,
                    "(",
                    localName.equals(genericName) ? EMPTY : "(Class) ",
                    writer.getClassConstant(localName) + COMMA + "forVariable(variable)",
                    hasEntityFields ? ", INITS" : EMPTY,
                    additionalParams,
                    ");"
            );
        }
        if (!hasEntityFields) {
            constructorContent(writer, model);
        }
        writer.end();

        if (!localName.equals(genericName)) {
            writer.suppressWarnings("all");
        }
        String classCast = localName.equals(genericName) ? EMPTY : "(Class) ";
        writer.beginConstructor(
                new Parameter("variable", Types.STRING),
                new Parameter("schema", Types.STRING),
                new Parameter("table", Types.STRING)
        );
        writer.line(SUPER + "(" + classCast + writer.getClassConstant(localName) + COMMA
                            + "forVariable(variable), schema, table);");
        constructorContent(writer, model);
        writer.end();

        writer.beginConstructor(
                new Parameter("variable", Types.STRING),
                new Parameter("schema", Types.STRING)
        );
        writer.line(SUPER + "(" + classCast + writer.getClassConstant(localName), COMMA,
                    "forVariable(variable), schema, \"", model.getData().get("table").toString(), "\");"
        );
        constructorContent(writer, model);
        writer.end();
    }

    private boolean superTypeHasEntityFields(EntityType model) {
        Supertype superType = model.getSuperType();
        return null != superType && null != superType.getEntityType()
                && superType.getEntityType().hasEntityFields();
    }

    private static CodeWriter suppressAllWarnings(CodeWriter writer) throws IOException {
        return writer.suppressWarnings("all", "rawtypes", "unchecked");
    }

    @Override
    protected void serializeProperties(
            EntityType model, SerializerConfig config, CodeWriter writer
    ) throws IOException
    {
        var newModel = withFieldOrderedProperties(model);
        super.serializeProperties(newModel, config, writer);
    }

    @Override
    protected void outro(EntityType model, CodeWriter writer) throws IOException {
        var newModel = withFieldOrderedProperties(model);
        super.outro(newModel, writer);
    }

    public CustomPropertiesEntityType withFieldOrderedProperties(EntityType entityType) {
        var properties = entityType.getProperties();
        var propertyNameToProperty = properties.stream().collect(Collectors.toMap(
                Property::getName,
                Function.identity()
        ));

        var typeElement = processingEnvironment.getElementUtils().getTypeElement(entityType.getFullName());
        var orderedProperties = ElementFilter.fieldsIn(new ArrayList<>(typeElement.getEnclosedElements())).stream().map(
                field -> propertyNameToProperty.get(field.getSimpleName().toString())).filter(Objects::nonNull).collect(
                Collectors.toCollection(LinkedHashSet::new));

        properties.stream().filter(property -> !orderedProperties.contains(property)).forEach(orderedProperties::add);

        addMetaData(orderedProperties);

        return new CustomPropertiesEntityType(entityType, orderedProperties);
    }

    private void addMetaData(LinkedHashSet<Property> orderedProperties) {
        List<Property> orderedPropertiesList = new ArrayList<>(orderedProperties);
        for (var i = 0; i < orderedPropertiesList.size(); i++) {
            var property = orderedPropertiesList.get(i);
            property.getData().put("COLUMN", ColumnMetadata.named(getColumnName(property)).withIndex(i));

        }
    }

    protected String getColumnName(Property property) {
        var parentType = processingEnvironment.getElementUtils().getTypeElement(property
                                                                                        .getDeclaringType()
                                                                                        .getFullName());
        return parentType
                .getEnclosedElements()
                .stream()
                .filter(element -> element instanceof VariableElement)
                .map(element -> (VariableElement) element)
                .filter(element -> element
                        .getSimpleName()
                        .toString()
                        .equals(property.getName()))
                .filter(element -> element.getAnnotation(Column.class) != null)
                .map(element -> element.getAnnotation(Column.class).value())
                .findAny()
                .orElseGet(() -> CaseFormat.LOWER_CAMEL.to(columnCaseFormat, property.getName()));
    }

    static class CustomPropertiesEntityType extends EntityType {

        private final Set<Property> fieldOrderedProperties;

        CustomPropertiesEntityType(EntityType entityType, Set<Property> fieldOrderedProperties) {
            super(entityType);
            this.fieldOrderedProperties = fieldOrderedProperties;
        }

        @Override
        public Set<Property> getProperties() {
            return fieldOrderedProperties;
        }

    }

}
