package com.graceful.skyward.common.proxy

interface IProxy {
    fun preInit()

    fun init()

    fun postInit()
}