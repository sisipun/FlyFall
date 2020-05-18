package io.cucumber.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.badlogic.gdx.net.HttpRequestBuilder
import io.cucumber.Game
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.ScreenManager
import io.cucumber.utils.constant.GameConstants.*

class HighScoreScreen(
        game: Game,
        private var bonusCount: Int,
        private var highScore: Int,
        private var isSoundOn: Boolean,
        private var isAcceleratorOn: Boolean,
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
                home()
            }

            override fun failed(t: Throwable?) {
                Gdx.app.log("High score", "Request failed", t)
                home()
            }

            override fun handleHttpResponse(httpResponse: Net.HttpResponse?) {
                Gdx.app.log("High score", "Request completed: $httpResponse")
            }
        })
    }

    fun init(bonusCount: Int, highScore: Int, isSoundOn: Boolean,
             isAcceleratorOn: Boolean, levelAssets: LevelAssets): HighScoreScreen {
        this.isSoundOn = isSoundOn
        this.isAcceleratorOn = isAcceleratorOn
        this.highScore = highScore
        this.bonusCount = bonusCount
        this.levelAssets = levelAssets

        Gdx.net.sendHttpRequest(findHighScoreRequest, object : Net.HttpResponseListener {
            override fun cancelled() {
                Gdx.app.log("High score", "Request cancelled")
                home()
            }

            override fun failed(t: Throwable?) {
                Gdx.app.log("High score", "Request failed", t)
                home()
            }

            override fun handleHttpResponse(httpResponse: Net.HttpResponse?) {
                Gdx.app.log("High score", "Request completed: $httpResponse")
            }
        })

        return this
    }

    private fun home() {
        setScreen(ScreenManager.getStartScreen(
                game,
                bonusCount,
                highScore,
                isSoundOn,
                isAcceleratorOn,
                levelAssets
        ))
    }
}