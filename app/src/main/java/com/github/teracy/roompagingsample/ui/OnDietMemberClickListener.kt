package com.github.teracy.roompagingsample.ui

import com.github.teracy.roompagingsample.data.paging.DietMember

interface OnDietMemberClickListener {
    fun onClick(member: DietMember?)
}