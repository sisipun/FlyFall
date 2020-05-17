package io.cucumber.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.badlogic.gdx.net.HttpRequestBuilder
import io.cucumber.Game
import io.cucumber.model.level.LevelAssets
import io.cucumber.utils.constant.GameConstants.*

class HighScoreScreen(
        game: Game,
        levelAssets: LevelAssets? = null
) : BaseScreen(game, levelAssets) {

    private val requestBuilder: HttpRequestBuilder = HttpRequestBuilder()
    private val findHighScoreRequest = requestBuilder.newRequest()
            .url(HOST + SCORE_PATH)
            .method(Net.HttpMethods.GET)
            .content("page=$DEFAULT_HIGH_SCORE_PAGE&size=$DEFAULT_HIGH_SCORE_SIZE")
            .build()

    init {
        Gdx.net.sendHttpRequest(findHighScoreRequest, object : Net.HttpResponseListener {
            override fun cancelled() {
                Gdx.app.log("High score", "Request cancelled")
            }

            override fun failed(t: Throwable?) {
                Gdx.app.log("High score", "Request failed", t)
            }

            override fun handleHttpResponse(httpResponse: Net.HttpResponse?) {
                Gdx.app.log("High score", "Request completed: $httpResponse")
            }
        })
    }

    fun init(levelAssets: LevelAssets?): HighScoreScreen {
        Gdx.net.sendHttpRequest(findHighScoreRequest, object : Net.HttpResponseListener {
            override fun cancelled() {
                Gdx.app.log("High score", "Request cancelled")
            }

            override fun failed(t: Throwable?) {
                Gdx.app.log("High score", "Request failed", t)
            }

            override fun handleHttpResponse(httpResponse: Net.HttpResponse?) {
                Gdx.app.log("High score", "Request completed: $httpResponse")
            }
        })

        return this
    }

}