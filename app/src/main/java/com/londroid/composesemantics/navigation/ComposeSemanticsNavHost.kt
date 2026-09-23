package com.londroid.composesemantics.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.londroid.composesemantics.screens.ClearSemanticsScreen
import com.londroid.composesemantics.screens.FontScalingScreen
import com.londroid.composesemantics.screens.HomeScreen
import com.londroid.composesemantics.screens.LiveRegionScreen
import com.londroid.composesemantics.screens.MergeScenariosScreen
import com.londroid.composesemantics.screens.NodeMergingScreen
import com.londroid.composesemantics.screens.SemanticsDefaultsScreen

object Routes {
    const val HOME = "home"
    const val NODE_MERGING = "node_merging"
    const val CLEAR_SEMANTICS = "clear_semantics"
    const val LIVE_REGION = "live_region"
    const val FONT_SCALING = "font_scaling"
    const val MERGE_SCENARIOS = "merge_scenarios"
    const val SEMANTICS_DEFAULTS = "semantics_defaults"
}

@Composable
fun ComposeSemanticsNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(onNavigate = { route -> navController.navigate(route) })
        }
        composable(Routes.NODE_MERGING) {
            NodeMergingScreen(onBack = navController::popBackStack)
        }
        composable(Routes.CLEAR_SEMANTICS) {
            ClearSemanticsScreen(onBack = navController::popBackStack)
        }
        composable(Routes.LIVE_REGION) {
            LiveRegionScreen(onBack = navController::popBackStack)
        }
        composable(Routes.FONT_SCALING) {
            FontScalingScreen(onBack = navController::popBackStack)
        }
        composable(Routes.MERGE_SCENARIOS) {
            MergeScenariosScreen(onBack = navController::popBackStack)
        }
        composable(Routes.SEMANTICS_DEFAULTS) {
            SemanticsDefaultsScreen(onBack = navController::popBackStack)
        }
    }
}
