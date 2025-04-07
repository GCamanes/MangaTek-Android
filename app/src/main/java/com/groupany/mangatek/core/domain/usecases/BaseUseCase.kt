package com.groupany.mangatek.core.domain.usecases

import com.groupany.mangatek.core.domain.FailureHandler

interface BaseUseCase<in Input, out Output> {
    suspend operator fun invoke(input: Input): Output
}

interface NoParamBaseUseCase<out Output> {
    suspend operator fun invoke(): Output
}

abstract class UseCase<in Input, out Output> : BaseUseCase<Input, Output>, FailureHandler() {}

abstract class NoParamUseCase<out Output> : NoParamBaseUseCase<Output>, FailureHandler() {}