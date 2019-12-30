package com.flicker.domain

/**
 * Created by Pratik Behera on 2019-12-30.
 */

interface RenderingView<in S> {
    /**
     * Feeds the View with data passed in the [viewState] parameter
     *
     * @param viewState View State, or a ViewModel, containing all data to be displayed by [RenderingView]
     *
     * NOTE: it's not meant to represent a partial state, but a complete snapshot of current view state every time.     *
     * @see <a href=http://hannesdorfmann.com/android/mosby3-mvi-1>http://hannesdorfmann.com/android/mosby3-mvi-1</a>
     */
    fun render(viewState: S)
}
