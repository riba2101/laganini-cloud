package org.laganini.cloud.rmi.client.feign;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.apache.commons.lang3.reflect.TypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

//public class FeignReactiveDecoder implements Decoder {
//
//    private final Decoder delegate;
//
//    public FeignReactiveDecoder(Decoder delegate) {
//        this.delegate = delegate;
//    }
//
//    @Override
//    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
//        ParameterizedType parameterizedType = (ParameterizedType) type;
//        Class<?>          clazz             = (Class<?>) parameterizedType.getRawType();
//
//        if (Mono.class.isAssignableFrom(clazz)) {
//            Type generic = getTargetType(parameterizedType);
//            if (isVoid(generic)) {
//                return Mono.defer(Mono::empty);
//            }
//
//            return Mono.just(delegate.decode(response, generic));
//        }
//
//        if (Flux.class.isAssignableFrom(clazz)) {
//            Type generic = getTargetType(parameterizedType);
//            if (isVoid(generic)) {
//                return Flux.defer(Flux::empty);
//            }
//
//            return Flux.fromIterable((Iterable<?>) delegate.decode(
//                    response,
//                    TypeUtils.parameterize(
//                            Collection.class,
//                            generic
//                    )
//            ));
//        }
//
//        return delegate.decode(response, type);
//    }
//
//    private Type getTargetType(ParameterizedType parameterizedType) {
//        return parameterizedType.getActualTypeArguments()[0];
//    }
//
//    private boolean isVoid(Type generic) {
//        return generic.equals(Void.class);
//    }
//
//}
