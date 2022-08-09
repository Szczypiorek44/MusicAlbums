package com.example.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.album.local.LocalDatabaseDAO
import com.example.data.album.remote.RemoteAlbumSource
import com.example.domain.utils.RxImmediateSchedulerRule
import com.example.domain.utils.albumListMock
import com.example.domain.utils.albumListResponseMock
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class AlbumRepositoryImplTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    @Mock
    lateinit var localDatabaseDaoMock: LocalDatabaseDAO

    @Mock
    lateinit var remoteAlbumSourceMock: RemoteAlbumSource


    private val albumRepository by lazy { AlbumRepositoryImpl(localDatabaseDaoMock, remoteAlbumSourceMock) }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
    }

    @Test()
    fun `skips first value after starting observing`() {
        whenever(localDatabaseDaoMock.observeAlbums()).thenReturn(Observable.just(albumListMock))

        Assert.assertThrows(
            "Expected to throw NoSuchElementException as first element is skipped",
            NoSuchElementException::class.java
        ) {
            albumRepository.observeAlbums().blockingLast()
        }
    }

    @Test
    fun `performs necessary database operations after refresh`() {
        whenever(localDatabaseDaoMock.getAlbums()).thenReturn(Single.just(albumListMock))
        whenever(localDatabaseDaoMock.clearAlbums()).thenReturn(Completable.complete())
        whenever(localDatabaseDaoMock.insertAlbums(any())).thenReturn(Completable.complete())
        whenever(remoteAlbumSourceMock.getAlbums(any())).thenReturn(Single.just(albumListResponseMock))

        albumRepository.refreshAlbums().subscribe()

        verify(localDatabaseDaoMock, times(1)).clearAlbums()
        verify(localDatabaseDaoMock, times(1)).insertAlbums(any())
        verify(localDatabaseDaoMock, times(1)).insertAlbums(any())
    }

    @Test
    fun `invokes necessary remote source methods after refresh`() {
        whenever(localDatabaseDaoMock.getAlbums()).thenReturn(Single.just(albumListMock))
        whenever(localDatabaseDaoMock.clearAlbums()).thenReturn(Completable.complete())
        whenever(localDatabaseDaoMock.insertAlbums(any())).thenReturn(Completable.complete())
        whenever(remoteAlbumSourceMock.getAlbums(any())).thenReturn(Single.just(albumListResponseMock))

        albumRepository.refreshAlbums().subscribe()

        verify(remoteAlbumSourceMock, times(1)).getAlbums(any())
    }

}