package com.lmy.controller;

import com.lmy.config.StartupConfig;

/**
 * Created by lmy on 2018/2/13.
 */
public abstract class BaseController {
    protected StartupConfig config;

    BaseController(StartupConfig config) {
        this.config = config;
    }
}
