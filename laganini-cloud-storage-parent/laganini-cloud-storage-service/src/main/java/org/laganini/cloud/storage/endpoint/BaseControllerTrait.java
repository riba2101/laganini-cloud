package org.laganini.cloud.storage.endpoint;

public interface BaseControllerTrait<ID, SERVICE, SUPPORT extends ControllerSupport<ID>> {

    SERVICE getService();

    SUPPORT getSupport();

}
