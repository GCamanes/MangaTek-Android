package com.groupany.mangatek.core.domain.usecases

import com.groupany.mangatek.core.domain.FailureHandler
import kotlinx.coroutines.flow.Flow

interface BaseUseCase<in Input, out Output> {
    suspend operator fun invoke(input: Input): Output
}

interface NoParamBaseUseCase<out Output> {
    suspend operator fun invoke(): Output
}

interface BlankBaseUseCase {
    suspend operator fun invoke()
}

interface BaseFlowUseCase<in Input, out Output> {
    suspend operator fun invoke(input: Input): Flow<Output>
}

interface BaseNoParamFlowUseCase<out Output> {
    suspend operator fun invoke(): Flow<Output>
}

abstract class UseCase<in Input, out Output> : BaseUseCase<Input, Output>, FailureHandler() {}

abstract class NoParamUseCase<out Output> : NoParamBaseUseCase<Output>, FailureHandler() {}

abstract class BlankUseCase : BlankBaseUseCase, FailureHandler() {}

abstract class FlowUseCase<in Input, out Output> : BaseFlowUseCase<Input, Output>, FailureHandler() {}

abstract class NoParamFlowUseCase<out Output> : BaseNoParamFlowUseCase<Output>, FailureHandler() {}