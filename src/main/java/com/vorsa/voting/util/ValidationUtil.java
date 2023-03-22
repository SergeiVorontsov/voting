package com.vorsa.voting.util;

import com.vorsa.voting.model.BaseEntity;

public class ValidationUtil {
    public static void checkNew(BaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + "must be new (id=null");
        }
    }

    public static void assureIdConsistent(BaseEntity entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalArgumentException(entity + "must has id=" + id);

        }
    }
}