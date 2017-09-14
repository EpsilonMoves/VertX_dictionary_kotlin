package server

import dictionary.Dictionary
import dictionary.DictionaryInterface
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import utils.*

/*
* server class - handles http requests and responses
* */
class Server : AbstractVerticle(){

    private lateinit var router: Router
    private lateinit var eb: EventBus

    /*
    * start the sever
    * */
    override fun start(fut: Future<Void>) {
        // register consumer
        eb = vertx.eventBus()
        eb.consumer<Any>(WORDS_CONSUMER) { message ->
            val dictionary: DictionaryInterface = Dictionary()
            message.reply(dictionary.dictionaryClosest(message.body().toString()))
        }

        // the router
        router = Router.router(vertx)
        router.post(PATH_ANALYZE).handler(BodyHandler.create())
        router.post(PATH_ANALYZE).handler { routingContext ->
            val inputWord: String?
            try {
                inputWord = routingContext.bodyAsJson.getString(INPUT_KEY_TEXT)
            if (inputWord.assertInput()) {// input is valid
                eb.send<Any>(WORDS_CONSUMER, inputWord.toString()) { res ->
                    if (res.succeeded()) {
                        routingContext.response()
                                .setStatusCode(HTTP_CODE_OK)
                                .putHeader(CONTENT_TYPE, DEFAULT_JSON_DEFAULT_CHARSET)
                                .end(Json.encodePrettily(res.result().body()))
                    }
                }
            } else {// input is not valid
                badRequestResponse(routingContext)

            }
            } catch (e: Exception) {// exception was thrown - probably when trying to get message body
                println(e.message)
                badRequestResponse(routingContext)
            }
        }
        // creating the server
        vertx.createHttpServer()
                .requestHandler { router.accept(it) }
                .listen( config().getInteger(HTTP_PORT, DEFAULT_PORT )) { result ->
                    if (result.succeeded()) {
                        fut.complete()
                    } else {
                        fut.fail(result.cause())
                    }
                }
    }

    private fun badRequestResponse(routingContext: RoutingContext) {
        routingContext.response()
                .setStatusCode(HTTP_CODE_BAD_REQUEST)
                .putHeader(CONTENT_TYPE, DEFAULT_JSON_DEFAULT_CHARSET)
                .end(Json.encodePrettily(INPUT_NOT_VALID))
    }
}


