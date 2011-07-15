#include <iostream>
#include <vector>
#include <algorithm>
#include <stdio.h>
using namespace std;

int last_one(string row){
    for (int i = row.length() -1 ; i >=0 ; --i){
        if (row[i] == '1')
            return i;
    }
    return 0;
}

int solve(){
    int n;
    cin >> n;
    vector<int> nums;
    for (int i = 0; i < n; ++i){
        string s;
        cin >> s;
        nums.push_back(last_one(s));
    }
    
    int swaps = 0;
    int tmp = 0;

    for (int i = 0; i < nums.size(); i++){
      if (nums[i] > i){
        // need to swap. 
        // figure out the min location this needs to end up.
        int idx = 0;
        for (idx = i; idx < n && nums[idx] > i; idx++){}
        //cout << i << " needs to go to " << idx << endl; 
        //reverse swap to the pos.
        for (int j = idx; j > i; j--){
           // printf(" swapping idx:%d[%d] <=> idx:%d[%d]\n", j-1, nums[j-1], j, nums[j]);
            tmp = nums[j];
            nums[j] = nums[j-1];
            nums[j-1] = tmp;
            swaps++;
          }
        }
    }
    return swaps; 
}

int main(){
    int t;
    cin >> t;
    ++t;
    for (int i = 1; i < t; ++i){
        cout << "Case #" << i << ": " << solve() << endl;
    }
}
