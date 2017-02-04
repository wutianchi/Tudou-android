/**
 * 本包里面存放一些基类，主要抽象了titlebar.
 * baseActivity：以activity为单个页面时的父类，主要抽象了titlebar
 * fragmentActivty:当多个fragment拥有几乎相同的titlebar时，推荐继承这个fragmentActivity
 *当各自的fragment的titlebar差异较大时，推荐在fragment的xml文件中include下custom_title
 */
package com.gunlei.app.ui.base;