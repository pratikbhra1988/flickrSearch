package com.flicker.domain.logging

/**
 * Created by Pratik Behera on 2019-09-03.
 */

/**
 * Architecture layer, specified for logging (filtering) purposes
 */
enum class Layer {
    /**
     * Activities, Fragments, layouts and auxiliary classes such as list adapters.
     * Related to UI operations and lifecycle of visual components.
     * To be used only within the app module.
     */
    VIEW,
    /**
     * Presenters - binding user input with interactors, handling lifecycle of attached views
     * and evaluating view states to be rendered in these views.
     * To be used within the domain module.
     */
    PRESENTER,
    /**
     * Interactors - business logic and interacting with physical entities such as API, database etc.
     * To be used within the app module
     */
    INTERACTOR,
    /**
     * Network operations - used by OkHttp logger for intercepting requests and responses
     * as well as any custom network-related code we might need (eg. detecting lack of connectivity)
     */
    NETWORK,
    /**
     * Database operations - saving and loading data to the persistence layer
     */
    DATABASE,
    /**
     * Identifies all actions related specifically to migrating user data over from the legacy app.
     * Note that operations related to migration can still be marked with different [Layer] values,
     * whenever generic routines are employed (eg. for database operations)
     */
    MIGRATION,
    /**
     * Code can't be classified as belonging to any of predefined layers.
     * The value will be skipped in logging output altogether
     */
    UNSPECIFIED
}
