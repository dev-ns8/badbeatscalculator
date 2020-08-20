package com.nate.service;

import com.nate.dao.StartHandDaoImpl;
import lombok.Getter;

public class Backend {

    @Getter private final StartHandDaoImpl startHandDao;

    private Backend(StartHandDaoImpl startHandDao) {
        this.startHandDao = startHandDao;
    }

}
