package com.mm.controller;

import com.mm.config.StartupConfig;

/**
 * Created by lmy on 2018/2/13.
 */
public abstract class BaseController {
    protected StartupConfig config;

    BaseController(StartupConfig config) {
        this.config = config;
    }
}
