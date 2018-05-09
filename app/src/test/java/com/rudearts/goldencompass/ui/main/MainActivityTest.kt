package com.rudearts.goldencompass.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rudearts.goldencompass.R
import com.rudearts.goldencompass.extentions.visible
import com.rudearts.goldencompass.extentions.visibleOrNot
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MainActivityTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var vieModelFactory:ViewModelProvider.Factory

    @InjectMocks @Spy lateinit var activity:MainActivityMock

    private val lblLocation = mock<TextView> {}
    private val imgArrow = mock<ImageView> {}
    private val lblLatitude = mock<TextView> {}
    private val lblLongitude = mock<TextView> {}

    @Before
    fun setup() {
        whenever(activity.lblLocation).thenReturn(lblLocation)
        whenever(activity.imgArrow).thenReturn(imgArrow)
        whenever(activity.lblLatitude).thenReturn(lblLatitude)
        whenever(activity.lblLongitude).thenReturn(lblLongitude)
    }


    @Test
    fun getTextByLocationState_WithTrue() {
        val result = activity.getTextByLocationState(true)

        assertEquals(R.string.stop, result)
    }

    @Test
    fun getTextByLocationState_WithFalse() {
        val result = activity.getTextByLocationState(false)

        assertEquals(R.string.start, result)
    }

    @Test
    fun changeLocationViews_WhenTrue() {
        activity.changeLocationViews(true)

        verify(lblLocation, times(1)).setText(R.string.stop)
        verify(imgArrow, times(1)).visible = true
        verify(lblLatitude, times(1)).visibleOrNot = true
        verify(lblLongitude, times(1)).visibleOrNot = true
    }

    @Test
    fun changeLocationViews_WhenFalse() {
        activity.changeLocationViews(false)

        verify(lblLocation, times(1)).setText(R.string.start)
        verify(imgArrow, times(1)).visible = false
        verify(lblLatitude, times(1)).visibleOrNot = false
        verify(lblLongitude, times(1)).visibleOrNot = false
    }

    /**
     * Ok, I gave up here - I had some problems with mocking activity
     * it was holiday, it was late and this "temporary" fix worked like a charm...
     */
    class MainActivityMock : MainActivity() {
        override fun <T : View?> findViewById(id: Int): T {
            return null as T
        }
    }
}