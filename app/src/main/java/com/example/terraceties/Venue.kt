package com.example.terraceties

import com.google.android.gms.maps.model.LatLng

data class Venue(
    val team: String,
    val name: String,
    val latLang: LatLng
)

object VenueList{
    fun getVenue(): List<Venue>{

        val itemVenue0 = Venue(
            "AFC Flyde",
            "Mill Farm Stadium",
            LatLng(53.797631526457785, -2.8894472929003694)
        )

        val itemVenue1 = Venue(
            "Aldershot Town",
            "The EBB Stadium ",
            LatLng(51.248637431490074, -0.7547312879238633)
        )
        val itemVenue2 = Venue(
            "Altrincham",
            "The J Davidson Stadium",
            LatLng(53.38347135816221, -2.335266458952101)
        )

        val itemVenue3 = Venue(
            "Barnet",
            "The Hive Stadium",
            LatLng(51.602717765047366, -0.29161242837996754)
        )
        val itemVenue4 = Venue(
            "Boreham Wood",
            "Meadow Park",
            LatLng(51.66226149774007, -0.2711762148834094)
        )
        val itemVenue5 = Venue(
            "Bromley",
            "RELOC8 EM Community Stadium",
            LatLng(51.39024326975506, 0.02033482426946219)
        )
        val itemVenue6 = Venue(
            "Chesterfield",
            "SMH Group Stadium",
            LatLng(53.25365481646232, -1.4256686012890378)
        )
        val itemVenue7 = Venue(
            "Dagenham & Redbridge",
            "Chigwell Construction Stadium",
            LatLng(51.54776253219306, 0.15988675072319927)
        )
        val itemVenue8 = Venue(
            "Dorking Wanderers",
            "Meadowbank Stadium",
            LatLng(51.234526090329844, -0.3331085247028039)
        )
        val itemVenue9 = Venue(
            "Eastleigh",
            "Silverlake Stadium",
            LatLng(50.9526018945784, -1.3719837302708557)
        )
        val itemVenue10 = Venue(
            "Ebbsfleet United ",
            "Kuflink Stadium",
            LatLng(51.44967409659684, 0.3223280120887625)
        )
        val itemVenue11 = Venue(
            "FC Halifax Town",
            "The Shay Stadium",
            LatLng(53.716322148942865, -1.8592265337950697)
        )
        val itemVenue12 = Venue(
            "Gateshead",
            "Gateshead International Stadium",
            LatLng(54.96090306905455, -1.5809973760419707)
        )
        val itemVenue13 = Venue(
            "Hartlepool United ",
            "The Suit Direct Stadium",
            LatLng(54.68920539506056, -1.2126465030454898)
        )
        val itemVenue14 = Venue(
            "Kidderminster Harriers",
            "Aggborough Stadium",
            LatLng(52.380449862551124, -2.2426667320309512)
        )
        val itemVenue15 = Venue(
            "Maidenhead United",
            "York Road Stadium",
            LatLng(51.519934575570396, -0.7179921032497217)
        )
        val itemVenue16 = Venue(
            "Oldham Athletic",
            "Boundary Park",
            LatLng(53.55524257402759, -2.1283486147622965)
        )
        val itemVenue17 = Venue(
            "Oxford city",
            "RAW Charging Stadium",
            LatLng(51.774087837548635, -1.2286062831069222)
        )
        val itemVenue18 = Venue(
            "Rochdale",
            "Crown Oil Arena",
            LatLng(53.62084478081814, -2.1800649338012685)
        )
        val itemVenue19 = Venue(
            "Solihull Moors",
            "ARMCO Arena",
            LatLng(52.43855378075847, -1.7567412166842906)
        )
        val itemVenue20 = Venue(
            "Southend United",
            "Roots Hall",
            LatLng(51.549125031767076, 0.7017694814091711)
        )
        val itemVenue21 = Venue(
            "Wealdstone",
            "Grosvenor Vale",
            LatLng(51.56951607059869, -0.4166042032466114)
        )
        val itemVenue22 = Venue(
            "Woking",
            "The Laithwaite Community Stadium",
            LatLng(51.3067081361213, -0.5586263744273817)
        )
        val itemVenue23 = Venue(
            "York City",
            "LNER Community Stadium",
            LatLng(53.98439698456022, -1.0529507895989432)
        )

        val venueList: ArrayList<Venue> = ArrayList()
        venueList.add(itemVenue0)
        venueList.add(itemVenue1)
        venueList.add(itemVenue2)
        venueList.add(itemVenue3)
        venueList.add(itemVenue4)
        venueList.add(itemVenue5)
        venueList.add(itemVenue6)
        venueList.add(itemVenue7)
        venueList.add(itemVenue8)
        venueList.add(itemVenue9)
        venueList.add(itemVenue10)
        venueList.add(itemVenue11)
        venueList.add(itemVenue12)
        venueList.add(itemVenue13)
        venueList.add(itemVenue14)
        venueList.add(itemVenue15)
        venueList.add(itemVenue16)
        venueList.add(itemVenue17)
        venueList.add(itemVenue18)
        venueList.add(itemVenue19)
        venueList.add(itemVenue20)
        venueList.add(itemVenue21)
        venueList.add(itemVenue22)
        venueList.add(itemVenue23)
        return venueList
    }
}
