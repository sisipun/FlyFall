package io.cucumber.controller

import com.badlogic.gdx.input.GestureDetector
import io.cucumber.view.BaseScreen

abstract class BaseController<T : BaseScreen>(screen: T) : GestureDetector.GestureAdapter()
