package alison.fivethingskotlin.viewmodel

import alison.fivethingskotlin.api.FiveThingsService
import alison.fivethingskotlin.model.FiveThings
import alison.fivethingskotlin.model.Resource
import alison.fivethingskotlin.model.Status
import alison.fivethingskotlin.model.Thing
import alison.fivethingskotlin.util.*
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class FiveThingsViewModel(private val fiveThingsService: FiveThingsService = FiveThingsService.create()) : ViewModel() {

    private val fiveThingsData = MutableLiveData<Resource<FiveThings>>()
    private val datesLiveData = MutableLiveData<Resource<List<Date>>>()

    private val disposables = CompositeDisposable()

    fun updateThings(token: String, things: Array<Thing>) {
        disposables.add(fiveThingsService.updateFiveThings(token, things)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { writtenDates ->
                            val days = writtenDates.map { getDateFromDatabaseStyle(it) }
                            datesLiveData.postValue(Resource(Status.SUCCESS, "A day was changed", days))
                        },
                        { error ->
                            datesLiveData.postValue(Resource(Status.ERROR, error.message, emptyList()))
                        }
                ))
    }

    fun saveNewThings(token: String, things: Array<Thing>) {
        disposables.add(fiveThingsService.writeFiveThings(token, things)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { writtenDates ->
                            val days = writtenDates.map { getDateFromDatabaseStyle(it) }
                            datesLiveData.postValue(Resource(Status.SUCCESS, "A day was changed", days))
                        },
                        { error ->
                            datesLiveData.postValue(Resource(Status.ERROR, error.message, emptyList()))
                        }
                ))
    }

    fun getThings(token: String, date: Date) {
        disposables.add(fiveThingsService.getFiveThings(token,  getYear(date).toString(),
                                                String.format("%02d", getMonthNumber(date)),
                                                String.format("%02d", getDay(date)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { things ->
                            val fiveThings = FiveThings(date, things, false, true)
                            fiveThingsData.postValue(Resource(Status.SUCCESS, "", fiveThings))
                        },
                        { error ->
                            if (error is retrofit2.HttpException && error.code() == 404) {
                                val things = FiveThings(date, listOf(
                                        Thing(getDatabaseStyleDate(date), "", 1),
                                        Thing(getDatabaseStyleDate(date), "", 2),
                                        Thing(getDatabaseStyleDate(date), "", 3),
                                        Thing(getDatabaseStyleDate(date), "", 4),
                                        Thing(getDatabaseStyleDate(date), "", 5)),
                                        false,
                                        false)
                                fiveThingsData.value = Resource(Status.SUCCESS, "Unwritten Day", things)
                            } else {
                                datesLiveData.postValue(Resource(Status.ERROR, error.message, null))
                            }
                        }
                ))
    }

    fun getDays(token: String) {
        disposables.add(fiveThingsService.getWrittenDates(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dates ->
                            val days = dates.map { getDateFromDatabaseStyle(it) }
                            datesLiveData.postValue(Resource(Status.SUCCESS, "", days))
                        },
                        { error ->
                            datesLiveData.postValue(Resource(Status.ERROR, error.message, null))
                        }
                ))
    }

    fun datesLiveData(): MutableLiveData<Resource<List<Date>>> {
        return datesLiveData
    }

    fun thingsLiveData(): MutableLiveData<Resource<FiveThings>> {
        return fiveThingsData
    }

    override fun onCleared() {
        disposables.clear()
    }

}