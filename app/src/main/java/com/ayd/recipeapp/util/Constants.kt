package com.ayd.recipeapp.util

class Constants {

    companion object{
        const val API_KEY = "bf6b765079a44d519c90e3331372715e"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
        const val BASE_URL = "https://api.spoonacular.com"

        const val RECIPE_RESULT_KEY  =  "recipeBundle" //Parcelable args'ları get,put yapmak için kullandığımız ortak key. retrofitle çektiğimiz verileri
        //bu keyi belirterek dağıtabileceğiz. fakat veri listesi ve "listenin üstündeki + veriyi alan "sınıfın" parcelable olması gerekiyor.
        //böylelikle retrofitle verileri bir kez çekiyoruz ve dağıtıyoruz ihtiyaca göre

        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //Room Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
        const val FAVORITE_RECIPES_TABLE = "favorite_recipes_table"
        const val FOOD_JOKE_TABLE = "food_joke_table"

        //bottom sheet and preferences
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCES_NAME = "food preferences"
        const val PREFERENCES_BACK_ONLINE = "backOnline"


    }

}