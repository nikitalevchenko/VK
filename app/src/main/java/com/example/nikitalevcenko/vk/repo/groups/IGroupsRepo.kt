package com.example.nikitalevcenko.vk.repo.groups

import android.arch.lifecycle.LiveData
import com.example.nikitalevcenko.vk.entity.Group

interface IGroupsRepo {
    fun groupCash(id: Long): LiveData<Group>
}
