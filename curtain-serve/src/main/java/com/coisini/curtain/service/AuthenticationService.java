package com.coisini.curtain.service;

import com.coisini.curtain.model.TokenGetModel;

public interface AuthenticationService {

    void getTokenByEmail(TokenGetModel userData);

    void validateByWx(TokenGetModel userData);

    void register();

}
