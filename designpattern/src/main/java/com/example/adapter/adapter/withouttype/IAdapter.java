package com.example.adapter.adapter.withouttype;

/**
 * Created by Administrator on 2017/12/19 0019.
 *
 *
 * 不在关注类型，只关心处理的结果，统一入口，面向适配器接口中的方法，
 * 具体的多变的适配者不在考虑，交给具体的适配器实现者去管理。
 *
 * 在这里适配器接口既是适配器也是目标接口。
 *
 *
 * adapter：可以分为正反连个方面来考虑：
 *
 * 不同的入口对象（各种类型的被适配者），统一的输出处理操作（handle（））。
 *
 * 统一的入口对象，不同的输出对象。<R> T adapt(Call<R> call);
 *
 *
 */
//本身作为接口+适配者（适配被适配者的方法，使得被适配者的方法可以被间接调用）
public interface IAdapter {

    public void handle(Object value);

}
