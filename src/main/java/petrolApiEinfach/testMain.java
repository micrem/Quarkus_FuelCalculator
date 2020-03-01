package petrolApiEinfach;

public class testMain {
    public static void main(String[] args) {
        PetrolStationApi petrolStationApiMosbach = new PetrolStationApi(49.337470,9.120130,PetrolTyp.e10);
        petrolStationApiMosbach.search();
    }
}
