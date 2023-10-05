package com.mobingc.personal.config.hibernate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.stereotype.Component;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * 注释集成器
 */
@Component
public class CommentIntegrator implements Integrator {

    public static final CommentIntegrator INSTANCE = new CommentIntegrator();

    public CommentIntegrator() {
        super();
    }

    /**
     * 类注释类型
     */
    private Class<? extends Annotation> classAnnotationType() {
        return ApiModel.class;
    }

    /**
     * 获取类注释
     */
    private String getClassComment(Annotation annotation) {
        return ((ApiModel) annotation).description();
    }

    /**
     * 属性注释类型
     */
    private Class<? extends Annotation> fieldAnnotationType() {
        return ApiModelProperty.class;
    }

    /**
     * 获取类注释
     */
    private String getFieldComment(Annotation annotation) {
        return ((ApiModelProperty) annotation).value();
    }

    /**
     * 集成执行
     */
    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        processComment(metadata);
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
    }

    /**
     * 注释集成流程
     */
    private void processComment(Metadata metadata) {
        for (PersistentClass persistentClass : metadata.getEntityBindings()) {
            // 处理类上的注释
            Class<?> clazz = persistentClass.getMappedClass();
            if (clazz.isAnnotationPresent(this.classAnnotationType())) {
                String comment = this.getClassComment(clazz.getAnnotation(this.classAnnotationType()));
                persistentClass.getTable().setComment(comment);
            }

            // 处理属性上的注释
            Property identifierProperty = persistentClass.getIdentifierProperty();
            if (identifierProperty != null) {
                fieldComment(persistentClass, identifierProperty.getName());
            } else {
                org.hibernate.mapping.Component component = persistentClass.getIdentifierMapper();
                if (component != null) {
                    // noinspection unchecked
                    Iterator<Property> iterator = component.getPropertyIterator();
                    while (iterator.hasNext()) {
                        fieldComment(persistentClass, iterator.next().getName());
                    }
                }
            }
            // noinspection unchecked
            Iterator<Property> iterator = persistentClass.getPropertyIterator();
            while (iterator.hasNext()) {
                fieldComment(persistentClass, iterator.next().getName());
            }
        }
    }

    /**
     * 字段注释
     */
    private void fieldComment(PersistentClass persistentClass, String columnName) {
        try {
            Field field = persistentClass.getMappedClass().getDeclaredField(columnName);
            if (!field.isAnnotationPresent(this.fieldAnnotationType())
                    || field.isAnnotationPresent(OneToMany.class)
                    || field.isAnnotationPresent(ManyToMany.class)
                    || field.isAnnotationPresent(OneToOne.class) && !"".equals(field.getAnnotation(OneToOne.class).mappedBy())) {
                return;
            }
            String comment = this.getFieldComment(field.getAnnotation(this.fieldAnnotationType()));
            String sqlColumnName = persistentClass.getProperty(columnName).getValue().getColumnIterator().next().getText();
            Iterator<org.hibernate.mapping.Column> columnIterator = persistentClass.getTable().getColumnIterator();
            while (columnIterator.hasNext()) {
                org.hibernate.mapping.Column column = columnIterator.next();
                if (sqlColumnName.equalsIgnoreCase(column.getName())) {
                    column.setComment(comment);
                    break;
                }
            }
        } catch (NoSuchFieldException | SecurityException ignored) {
        }
    }

}