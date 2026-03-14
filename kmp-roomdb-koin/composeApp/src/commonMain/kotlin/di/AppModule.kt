package di

import edu.gvsu.cis.kmp_roomdb.AppViewModel
import edu.gvsu.cis.kmp_roomdb.db.getDaoInstance
import edu.gvsu.cis.kmp_roomdb.db.getDatabaseInstance
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.dsl.module

expect fun platformDBModule(): Module

val appModule = module {
    singleOf(::getDatabaseInstance)
    singleOf(::getDaoInstance)
    viewModel<AppViewModel> {
        AppViewModel(get())
    }
}

fun initKoin(config: KoinAppDeclaration? = null) = startKoin {
    includes(config)
    modules(platformDBModule(), appModule)
}

