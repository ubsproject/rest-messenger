package com.ubs.messenger.service;

import java.util.List;

public interface CacheRepository {

    String MESSAGE_CACHE_NAME = "message";

    List<?> getAllEntries();
}
