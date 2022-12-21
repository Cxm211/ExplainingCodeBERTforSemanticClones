public class Clone291AllCodeParts { 
public MyViewHolder (View view) { 
     super (view); 
     view.setOnClickListener ((v) -> { 
         int adapterPosition = getAdapterPosition (); 
         if (adapterPosition >= 0) { 
             clicks.onItemSelected (myObject, adapterPosition); 
         } 
     }); 
 } 
 
public MyViewHolder (View itemView) {
image=(ImageView)itemView.findViewById(R.id.horizontal_list_image);
title=(TextView)itemView.findViewById(R.id.horizontal_list_title);
price=(TextView)itemView.findViewById(R.id.horizontal_list_price);
image.setOnClickListener(this);
title.setOnClickListener(this);
price.setOnClickListener(this);
}

}