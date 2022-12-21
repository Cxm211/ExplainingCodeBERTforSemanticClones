public class Clone23AllCodeParts { 
public double getSum () {
if(subAccounts != null)
Double sum=0.0;
for([int i=0] i < subAccounts.size() [i++])
sum+=subAccounts.get(i).getSum();
return amount + sum;
return amount;
}
 
 public double getSum () { 
     double result = this.amount; 
     if (this.subAccounts != null) { 
         for (Balance subAccount : this.subAccounts) { 
             result = result + subAccount.getSum (); 
         } 
     } 
     return result; 
 }

}