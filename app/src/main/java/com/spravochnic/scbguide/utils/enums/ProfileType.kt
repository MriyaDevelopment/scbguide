package com.spravochnic.scbguide.utils.enums

import com.spravochnic.scbguide.R

enum class ProfileType(val text: String, val icon: Int?) {
    MY_LECTURES("Мои лекции", R.drawable.ic_my_lecture),
    MY_TESTS("Мои тесты", R.drawable.ic_test),
    PRIVACY_POLICY("Политика конфиденциальности", R.drawable.ic_privacy_policy),
    TERMS_OF_SERVICE("Условия обслуживания", R.drawable.ic_terms_service),
    EXIT("Выйти", null),
    DELETE("Удалить аккаунт", null)
}