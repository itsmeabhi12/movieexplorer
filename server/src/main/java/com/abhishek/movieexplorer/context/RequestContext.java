package com.abhishek.movieexplorer.context;

import java.util.UUID;

public final class RequestContext {

    private static final ThreadLocal<RequestContext> HOLDER = new ThreadLocal<>();

    private final String requestId;
    private final UUID userId;

    private RequestContext(String requestId, UUID userId) {
        this.requestId = requestId;
        this.userId = userId;

    }

    public static void set(String requestId, UUID userId) {
        HOLDER.set(new RequestContext(requestId, userId));
    }

    public static RequestContext get() {
        return HOLDER.get();
    }

    public static void clear() {
        HOLDER.remove();
    }

    public String getRequestId() {
        return requestId;
    }

    public UUID getUserId() {
        return userId;
    }

}