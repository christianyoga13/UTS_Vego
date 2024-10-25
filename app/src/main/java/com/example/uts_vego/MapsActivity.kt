import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_vego.R
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.util.GeoPoint

class MapsActivity : AppCompatActivity(), View.OnTouchListener {
    private lateinit var map: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Konfigurasi OSM
        Configuration.getInstance().userAgentValue = packageName

        // Inisialisasi MapView
        map = findViewById(R.id.map)
        map.setMultiTouchControls(true)

        // Atur posisi dan zoom peta
        val mapController = map.controller
        mapController.setZoom(15.0)
        mapController.setCenter(GeoPoint(-6.2088, 106.8456)) // Jakarta sebagai contoh

        // Tambahkan marker untuk lokasi pilihan
        val startPoint = GeoPoint(-6.2088, 106.8456)
        val startMarker = Marker(map)
        startMarker.position = startPoint
        startMarker.title = "Lokasi Saya"
        map.overlays.add(startMarker)

        // Menambahkan Listener untuk Klik pada Peta
        map.setOnTouchListener(this) // Menggunakan this karena MapsActivity mengimplementasikan OnTouchListener
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            // Mengonversi IGeoPoint ke GeoPoint
            val geoPoint = GeoPoint(map.projection.fromPixels(event.x.toInt(), event.y.toInt()))
            // Lakukan sesuatu dengan geoPoint, seperti menambahkan marker
            val newMarker = Marker(map)
            newMarker.position = geoPoint
            newMarker.title = "Marker Baru"
            map.overlays.add(newMarker)
            map.invalidate() // Untuk menyegarkan tampilan peta
            return true // Mengindikasikan bahwa sentuhan telah diproses
        }
        return false
    }
}
