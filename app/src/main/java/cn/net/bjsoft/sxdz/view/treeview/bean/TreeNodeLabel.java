package cn.net.bjsoft.sxdz.view.treeview.bean;

/**
 * Created by Zrzc on 2017/3/13.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TreeNodeLabel
{  }
