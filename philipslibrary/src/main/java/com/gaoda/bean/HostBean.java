package com.gaoda.bean;

import java.io.Serializable;
import java.util.List;

public class HostBean implements Serializable{


    /**
     * path : /mqtt?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIAPUTEBCXPMNXJGTIQ%2F20180411%2Fcn-north-1%2Fiotdevicegateway%2Faws4_request&X-Amz-Date=20180411T073035Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=632b5778266fe0eb4c02603f90602ccc6c43f25c9c51b392007b91b7d4941ee6&X-Amz-Security-Token=FQoDYXdzELj//////////wEaDLAIgUodHASP8sa4liKOBPvNmDSBEcEMEMokkUtkP/lYyoKFFODl5bc%2B%2BKGBPLQPe6B11HYNf%2Be59W3eNjy2P0GAO5rFuX2l22yQxGCA1PO7cGQnHyz%2BnFpPWkEdQDohasypm7wZPaLtWL%2BzQIqXUH013XW0gEAr4WkrFF/vZjGrL2g7ODGPUTQ%2BBZfvS9NYw6fzTaf8b89Q6IMTcty2edhn90iSIBtCnC1LmJUeqa%2BkMGTSYLhJHRZbmHrTtjl2GF9LCh/bdpp8Y7SWLELUP/CBzHfUp6w767/30KVtnKrLT%2BzMqqHCCDGySvMKzFw0DG0B9tzLeAul1OyIcoy5fIeELYSTECkuTp54N9tyjDC4C5WSkgxz3UiVifnPBbkYf5BH4%2BYs1WgDieW8VXit84E0U%2BFjVMQ%2BXk60bf9VH37P1JfY7Mv%2BPzEFG6po8DqLuqYkrzHJFMvkvtqaQjZXV2T4tim3mkv%2BjOnK0CpO5SsgqhoG1g8ps24f3LG2Rx4WiebYES/Jw6TKRWgZgjUefrcQuSeqVe%2BULrcOWlDhzykKhn7KB8jx4jZfYyRHSAAqUcJ1qt2QC12X2L2cLKD0iNJgdi/2Zs0kdUQ5U%2BzADLTs6P/mjK8zKiVjBWBLE0L%2Brglu5iOLjLBfqcJJP%2BU8G4I73lv%2B5HJS5aRK35AWjfOy1PNv2vcarAvYArPQqYjXYlD9q2%2BKirWdIh7Sf9som/O21gU%3D
     * host : wss://a3o62fhmrrmmgs.iot.cn-north-1.amazonaws.com.cn/mqtt?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIAPUTEBCXPMNXJGTIQ%2F20180411%2Fcn-north-1%2Fiotdevicegateway%2Faws4_request&X-Amz-Date=20180411T073035Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=632b5778266fe0eb4c02603f90602ccc6c43f25c9c51b392007b91b7d4941ee6&X-Amz-Security-Token=FQoDYXdzELj//////////wEaDLAIgUodHASP8sa4liKOBPvNmDSBEcEMEMokkUtkP/lYyoKFFODl5bc%2B%2BKGBPLQPe6B11HYNf%2Be59W3eNjy2P0GAO5rFuX2l22yQxGCA1PO7cGQnHyz%2BnFpPWkEdQDohasypm7wZPaLtWL%2BzQIqXUH013XW0gEAr4WkrFF/vZjGrL2g7ODGPUTQ%2BBZfvS9NYw6fzTaf8b89Q6IMTcty2edhn90iSIBtCnC1LmJUeqa%2BkMGTSYLhJHRZbmHrTtjl2GF9LCh/bdpp8Y7SWLELUP/CBzHfUp6w767/30KVtnKrLT%2BzMqqHCCDGySvMKzFw0DG0B9tzLeAul1OyIcoy5fIeELYSTECkuTp54N9tyjDC4C5WSkgxz3UiVifnPBbkYf5BH4%2BYs1WgDieW8VXit84E0U%2BFjVMQ%2BXk60bf9VH37P1JfY7Mv%2BPzEFG6po8DqLuqYkrzHJFMvkvtqaQjZXV2T4tim3mkv%2BjOnK0CpO5SsgqhoG1g8ps24f3LG2Rx4WiebYES/Jw6TKRWgZgjUefrcQuSeqVe%2BULrcOWlDhzykKhn7KB8jx4jZfYyRHSAAqUcJ1qt2QC12X2L2cLKD0iNJgdi/2Zs0kdUQ5U%2BzADLTs6P/mjK8zKiVjBWBLE0L%2Brglu5iOLjLBfqcJJP%2BU8G4I73lv%2B5HJS5aRK35AWjfOy1PNv2vcarAvYArPQqYjXYlD9q2%2BKirWdIh7Sf9som/O21gU%3D
     * endpoint : a3o62fhmrrmmgs.iot.cn-north-1.amazonaws.com.cn
     * device_list : ["4391c0c0333c11e8ad571008b1d7bcf2","7b6f4880365911e891501008b1d7bcf2"]
     * client_id : 260a7ff0335111e8a8fff8cab81d2c1a
     */

    private String path;
    private String host;
    private String endpoint;
    private String client_id;
    private List<String> device_list;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public List<String> getDevice_list() {
        return device_list;
    }

    public void setDevice_list(List<String> device_list) {
        this.device_list = device_list;
    }
}
