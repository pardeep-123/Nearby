package com.creation.nearby.listeners

import android.view.View

interface OnActionListener<T> {

    fun notify(model: T, position: Int,view: View )


}