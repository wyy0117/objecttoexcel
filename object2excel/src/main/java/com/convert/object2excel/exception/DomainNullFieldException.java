package com.convert.object2excel.exception;

/**
 * @Author: wyy
 * @Date: 18-11-15 下午5:45
 */
public class DomainNullFieldException extends RuntimeException {

    public DomainNullFieldException(Object domain, String fieldName) {
        super(fieldName + "is null," + domain.toString());
    }
}
