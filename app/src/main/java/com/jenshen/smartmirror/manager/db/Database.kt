package ua.regin.pocket.manager.db

import io.reactivex.Maybe
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.Sort

interface Database {

    fun <T : RealmModel> add(model: T): T

    fun <T : RealmModel> add(model: List<T>): List<T>

    fun <T : RealmModel> get(clazz: Class<T>, primaryKey: String = "key", value: String): Maybe<T>

    fun <T : RealmModel> getAll(clazz: Class<T>): Maybe<RealmResults<T>>

    fun <T : RealmModel> getAsync(clazz: Class<T>, primaryKey: String, value: String): Maybe<T>

    fun <T : RealmModel> getAllAsync(clazz: Class<T>): Maybe<RealmResults<T>>

    fun <T : RealmModel> getAllAsyncSorted(clazz: Class<T>, fieldName: String, sort: Sort = Sort.DESCENDING): Maybe<RealmResults<T>>

    fun <T : RealmObject> listRealmObjectsFiltered(tClass: Class<T>,
                                                   fieldName: String,
                                                   value: Boolean): Maybe<RealmResults<T>>

    fun <T : RealmObject> listRealmObjectsFilteredSorted(tClass: Class<T>,
                                                         fieldName: String, value: String,
                                                         sortBy: String, sort: Sort = Sort.DESCENDING): Maybe<RealmResults<T>>
}
