package com.example.appbar.ui.tables;

public class p {
//
//    // creating a variable for recycler view,
//    // array list and adapter class.
//    private RecyclerView courseRV;
//    private ArrayList<RecyclerData> recyclerDataArrayList;
//    private RecyclerViewAdapter recyclerViewAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // initializing our variables.
//        courseRV = findViewById(R.id.idRVCourse);
//
//        // creating new array list.
//        recyclerDataArrayList = new ArrayList<>();
//
//        // in below line we are adding data to our array list.
//        recyclerDataArrayList.add(new RecyclerData("DSA Course", "DSA Self Paced Course"));
//        recyclerDataArrayList.add(new RecyclerData("C++ Course", "C++ Self Paced Course"));
//        recyclerDataArrayList.add(new RecyclerData("Java Course", "Java Self Paced Course"));
//        recyclerDataArrayList.add(new RecyclerData("Python Course", "Python Slef Paced Course"));
//        recyclerDataArrayList.add(new RecyclerData("Fork CPP", "Fork CPP Self Paced Course"));
//        recyclerDataArrayList.add(new RecyclerData("Amazon SDE", "Amazon SDE Test Questions"));
//
//        // initializing our adapter class with our array list and context.
//        recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, this);
//
//        // below line is to set layout manager for our recycler view.
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//
//        // setting layout manager for our recycler view.
//        courseRV.setLayoutManager(manager);
//
//        // below line is to set adapter
//        // to our recycler view.
//        courseRV.setAdapter(recyclerViewAdapter);
//
//        // on below line we are creating a method to create item touch helper
//        // method for adding swipe to delete functionality.
//        // in this we are specifying drag direction and position to right
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                // this method is called
//                // when the item is moved.
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                // this method is called when we swipe our item to right direction.
//                // on below line we are getting the item at a particular position.
//                RecyclerData deletedCourse = recyclerDataArrayList.get(viewHolder.getAdapterPosition());
//
//                // below line is to get the position
//                // of the item at that position.
//                int position = viewHolder.getAdapterPosition();
//
//                // this method is called when item is swiped.
//                // below line is to remove item from our array list.
//                recyclerDataArrayList.remove(viewHolder.getAdapterPosition());
//
//                // below line is to notify our item is removed from adapter.
//                recyclerViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//
//                // below line is to display our snackbar with action.
//                Snackbar.make(courseRV, deletedCourse.getTitle(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // adding on click listener to our action of snack bar.
//                        // below line is to add our item to array list with a position.
//                        recyclerDataArrayList.add(position, deletedCourse);
//
//                        // below line is to notify item is
//                        // added to our adapter class.
//                        recyclerViewAdapter.notifyItemInserted(position);
//                    }
//                }).show();
//            }
//            // at last we are adding this
//            // to our recycler view.
//        }).attachToRecyclerView(courseRV);
//    }
}
