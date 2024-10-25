import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.util.GeoPoint

class MapsActivity : AppCompatActivity() {
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

        // Mendengarkan klik pada peta untuk menambah marker
        map.setOnMapClickListener { geoPoint ->
            // Hapus marker sebelumnya jika ada
            map.overlays.clear()

            // Tambahkan marker baru di lokasi yang diklik
            val newMarker = Marker(map)
            newMarker.position = geoPoint
            newMarker.title = "Lokasi Pengiriman"
            map.overlays.add(newMarker)

            // Menampilkan informasi lokasi
            val latitude = geoPoint.latitude
            val longitude = geoPoint.longitude

            // Tampilkan dialog atau informasi lain
            showOrderDialog(latitude, longitude)
        }

    }
}
