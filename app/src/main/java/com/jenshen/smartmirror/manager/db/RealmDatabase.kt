package com.jenshen.smartmirror.manager.db

import io.reactivex.Maybe
import io.realm.*
import ua.regin.pocket.manager.db.Database
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealmDatabase : Database {

    @Inject constructor()

    override fun <T : RealmModel> add(model: T): T {
        val realm = getRealm()
        realm.executeTransactionAsync { it.copyToRealmOrUpdate(model) }
        closeRealm(realm)
        return model
    }

    override fun <T : RealmModel> add(model: List<T>): List<T> {
        val realm = getRealm()
        realm.executeTransactionAsync { it.copyToRealmOrUpdate(model) }
        closeRealm(realm)
        return model
    }

    override fun <T : RealmModel> get(clazz: Class<T>, primaryKey: String, value: String): Maybe<T> {
        val realm = getRealm()
        return Maybe.fromCallable {
            val result = realm.where(clazz).equalTo(primaryKey, value).findFirst()
            result
        }.doOnDispose { closeRealm(realm) }
    }

    override fun <T : RealmModel> getAll(clazz: Class<T>): Maybe<RealmResults<T>> {
        val realm = getRealm()
        return Maybe.fromCallable {
            val result = realm.where(clazz).findAll()

            result
        }.doOnDispose { closeRealm(realm) }
    }

    override fun <T : RealmModel> getAllAsync(clazz: Class<T>): Maybe<RealmResults<T>> {
        val realm = getRealm()
        val fromCallable = Maybe.fromCallable {
            realm.where(clazz).findAllAsync()
        }
        return fromCallable.doOnDispose { closeRealm(realm) }
    }

    override fun <T : RealmModel> getAllAsyncSorted(clazz: Class<T>, fieldName: String, sort: Sort): Maybe<RealmResults<T>> {
        val realm = getRealm()
        val fromCallable = Maybe.fromCallable {
            realm.where(clazz).findAllSortedAsync(fieldName, sort)
        }
        return fromCallable.doOnDispose { closeRealm(realm) }
    }

    override fun <T : RealmModel> getAsync(clazz: Class<T>, primaryKey: String, value: String): Maybe<T> {
        val realm = getRealm()
        val fromCallable = Maybe.fromCallable({
            realm.where(clazz).equalTo(primaryKey, value).findFirstAsync()
        })
        return fromCallable.doOnDispose { closeRealm(realm) }
    }

    override fun <T : RealmObject> listRealmObjectsFiltered(tClass: Class<T>,
                                                            fieldName: String, value: Boolean): Maybe<RealmResults<T>> {
        val realm = getRealm()
        val fromCallable = Maybe.fromCallable {
            realm.where(tClass).equalTo(fieldName, value).findAll()
        }
        return fromCallable.doOnDispose { closeRealm(realm) }
    }

    override fun <T : RealmObject> listRealmObjectsFilteredSorted(tClass: Class<T>,
                                                                  fieldName: String, value: String, sortBy: String, sort: Sort): Maybe<RealmResults<T>> {
        val realm = getRealm()
        val fromCallable = Maybe.fromCallable {
            realm.where(tClass).equalTo(fieldName, value).findAllSortedAsync(sortBy, sort)
        }
        return fromCallable.doOnDispose { closeRealm(realm) }
    }


    private fun closeRealm(realm: Realm?) {
        if (realm != null && !realm.isClosed) {
            realm.close()
        }
    }

    private fun getRealm(): Realm {
        return Realm.getDefaultInstance()
    }
}