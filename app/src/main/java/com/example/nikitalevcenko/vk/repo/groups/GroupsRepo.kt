package com.example.nikitalevcenko.vk.repo.groups

import com.example.nikitalevcenko.vk.db.Database

class GroupsRepo(private val db: Database) : IGroupsRepo {
    override fun groupCash(id: Long) = db.groups().group(id)
}