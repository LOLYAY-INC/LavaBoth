// Full Path: src/io/lolyay/jlavalink/v4/rest/handlers/resthandlers/loadtracks/RestLoadResult.java
package io.lolyay.jlavalink.v4.rest.handlers.resthandlers;

import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.loadtracks.LoadResultType;
import io.lolyay.jlavalink.v4.rest.model.RestResponse;

/**
 * Generic base class for all loadtracks results.
 * @param <T> The type of the 'data' field.
 */
public class RestLoadResult<T> implements RestResponse {
    @Expose
    public LoadResultType loadType;

    @Expose
    public T data; // The data field is now generic, preventing conflicts.
}