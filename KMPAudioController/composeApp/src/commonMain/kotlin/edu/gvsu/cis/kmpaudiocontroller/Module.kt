package edu.gvsu.cis.kmpaudiocontroller

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<AppViewModel> {
        AppViewModel()
    }
}
