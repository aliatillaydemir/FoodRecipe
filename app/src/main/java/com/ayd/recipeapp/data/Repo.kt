package com.ayd.recipeapp.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repo @Inject constructor(
    remoteDataSource: RemoteDataSource
){
    val remote = remoteDataSource
}