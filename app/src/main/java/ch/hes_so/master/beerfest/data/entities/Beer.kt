package ch.hes_so.master.beerfest.data.entities

import androidx.room.*

@Entity(tableName = "beer")
data class Beer(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val type: String,
    val brewery: Int,
    val acool: Float,
    val IBU: Float,
    val description: String,
    val color: String,
    val imageUUID: String
) {

    enum class BeerType(val type: String) {
        IPA("IPA"), WHITE("White"), DDH("DDH"), DIPA("DIPA"), DDIPA("DDHIPA"), LAGER("Lager"), ALE("Ale"), PALE_ALE(
            "Pale Ale"
        ),
        ALTBEER("Alt"), GARDE_BEER("Garde"), BROWN_ALE("Brown Ale"), PILS("Pilsner"), STOUT("Stout"), PORTER(
            "Porter"
        ),
        BELGIAN("Belge")
    }

    enum class BeerColor(val color: String) {
        BROWN("Brown"), WHITE("White"), AMBER("Ambrée"), BLACK("Noir"), BLONDE("Blonde")
    }

    enum class FermType(val fermentation: String) {
        LOW("Basse"), HIGH("Haute"), MIXTE("Mixte")
    }

    enum class Flavour(val flavour: String) {
        VANILLA("Vanille"), COFFE("Caffé"), CACAO("Cacao"), ORANGE("Orange"), LIME("Citron vert"), MANGO(
            "Mangue"
        ),
        PINEAPPLE("Ananas"), LEMON("Citron")
    }
}

