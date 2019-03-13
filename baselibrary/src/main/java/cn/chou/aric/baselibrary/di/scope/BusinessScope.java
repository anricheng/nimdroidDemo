package cn.chou.aric.baselibrary.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * cn.chou.aric.baselibrary.di
 * Created by Aric on 上午11:00.
 */

@Scope
@Documented
@Retention(RUNTIME)
public @interface BusinessScope {}

