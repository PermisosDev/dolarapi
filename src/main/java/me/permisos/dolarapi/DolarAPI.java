package me.permisos.dolarapi;

import io.activej.config.Config;
import io.activej.http.*;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import io.activej.inject.module.Module;
import io.activej.launcher.Launcher;
import io.activej.launchers.http.HttpServerLauncher;
import io.activej.promise.Promise;
import me.permisos.dolarapi.provider.ExchangeRatesProvider;

import java.net.InetSocketAddress;

import static io.activej.config.Config.ofSystemProperties;
import static io.activej.config.converter.ConfigConverters.*;

public class DolarAPI extends HttpServerLauncher {

    @Provides
    AsyncServlet servlet() {
    return RoutingServlet.create()
        .map(
            HttpMethod.GET,
            "/",
            request ->
                Promise.of(
                    HttpResponse.ok200().withPlainText("Available endpoints: \n/status\n/rates")
                )
        )
        .map(
             HttpMethod.GET,
             "/status",
             request -> Promise.of(
                     HttpResponse.ofCode(200).withPlainText("Im Alive !")
             )
        )
        .map(
            HttpMethod.GET,
            "/rates",
            request ->
                Promise
                        .of(HttpResponse.ok200().withJson(ExchangeRatesProvider.getAllRatesJSON())
                )
        );
    }

    @Override
    protected Module getOverrideModule() {
        return new AbstractModule() {
            @Provides
            Config config() {
                return Config.create()
                        .with("http.listenAddresses", Config.ofValue(ofInetSocketAddress(), new InetSocketAddress(9090)))
                        .with("workers", Config.ofValue(ofInteger(), Runtime.getRuntime().availableProcessors()))
                        .overrideWith(ofSystemProperties("config"));
            }
        };
    }
    public static void main(String[] args) throws Exception {
        Launcher launcher = new DolarAPI();
        launcher.launch(args);
    }
}
