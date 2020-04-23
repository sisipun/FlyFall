package io.cucumber.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import io.cucumber.Game
import io.cucumber.model.component.text.TextLabel
import io.cucumber.model.font.FontParams
import io.cucumber.utils.constant.GameConstants.*
import io.cucumber.utils.helper.FontHelper

class LoadScreen(
        private val game: Game
) : ScreenAdapter() {

    private val loadingFont = FontHelper.toFont(DEFAULT_FONT, FontParams(50, Color.GOLD))
    private val title: TextLabel = TextLabel(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT / 2,
            LOADING_LABEL_TEXT,
            this.loadingFont
    )

    override fun show() {
        super.show()
        game.stage.addActor(title)
    }

    override fun hide() {
        super.hide()
        game.stage.clear()
        loadingFont.dispose()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        Thread(Runnable { Gdx.app.postRunnable {
            game.init()
        }}).start()
        game.stage.draw()

        super.render(delta)
    }
}
