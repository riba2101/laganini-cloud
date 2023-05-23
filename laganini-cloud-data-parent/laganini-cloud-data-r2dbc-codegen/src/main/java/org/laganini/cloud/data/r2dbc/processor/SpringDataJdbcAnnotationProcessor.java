package org.laganini.cloud.data.r2dbc.processor;

import com.google.auto.service.AutoService;
import com.google.common.base.CaseFormat;
import com.infobip.spring.data.jdbc.annotation.processor.CustomExtendedTypeFactory;
import com.infobip.spring.data.jdbc.annotation.processor.ProjectColumnCaseFormat;
import com.infobip.spring.data.jdbc.annotation.processor.ProjectTableCaseFormat;
import com.infobip.spring.data.jdbc.annotation.processor.SpringDataJdbcAnnotationProcessorBase;
import com.querydsl.apt.Configuration;
import com.querydsl.codegen.*;
import com.querydsl.sql.codegen.NamingStrategy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Embedded;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

//@AutoService(Processor.class)
public class SpringDataJdbcAnnotationProcessor extends SpringDataJdbcAnnotationProcessorBase {


    private       CaseFormat     projectColumnCaseFormat;
    private final NamingStrategy namingStrategy;

    public SpringDataJdbcAnnotationProcessor() throws InstantiationException, IllegalAccessException {
        super(SpringDataJdbcQuerydslNamingStrategy.class);

        this.namingStrategy = SpringDataJdbcQuerydslNamingStrategy.class.newInstance();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        this.projectColumnCaseFormat = getProjectCaseFormat(
                roundEnv,
                ProjectColumnCaseFormat.class,
                ProjectColumnCaseFormat::value
        );

        return super.process(annotations, roundEnv);
    }

    private <A extends Annotation> CaseFormat getProjectCaseFormat(
            RoundEnvironment roundEnv,
            Class<A> annotation,
            Function<A, CaseFormat> valueExtractor
    )
    {
        return Optional
                .ofNullable(roundEnv.getElementsAnnotatedWith(annotation))
                .filter(elements -> elements.size() == 1)
                .map(elements -> elements.iterator().next())
                .map(element -> element.getAnnotation(annotation))
                .map(valueExtractor)
                .orElse(CaseFormat.UPPER_CAMEL);
    }

    @Override
    protected Configuration createConfiguration(RoundEnvironment roundEnv) {
        Class<? extends Annotation> entity        = Id.class;
        var                         codegenModule = new CodegenModule();
        var                         typeMappings  = new JavaTypeMappings();
        codegenModule.bind(TypeMappings.class, typeMappings);
        codegenModule.bind(QueryTypeFactory.class, new QueryTypeFactoryImpl("Q", "", ""));

        super.createConfiguration(roundEnv);
        return new SpringDataJdbcConfiguration(
                roundEnv,
                processingEnv,
                projectColumnCaseFormat,
                entity,
                null,
                null,
                Embedded.class,
                Transient.class,
                typeMappings,
                codegenModule,
                namingStrategy
        );
    }

}
