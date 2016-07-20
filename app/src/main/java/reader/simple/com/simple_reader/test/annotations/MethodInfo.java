package reader.simple.com.simple_reader.test.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/6/20 上午11:42
 * 备注：
 * Version：
 * ==================================================
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Inherited
@Target(ElementType.METHOD)
public @interface MethodInfo {
    String author() default "wxl";

    String date();

    int version() default 1;
}
