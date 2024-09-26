# ICS311 Assignment 2: A List of Sayings: ʻōlelo Noʻeau
 
Database built with an AVL-Tree to store Japanese sayings, their translations, and meanings.

# Explanation

## Why Use an AVL Tree?

An AVL tree is ideal for this type of application because it guarantees efficient search, insertion, and deletion operations with a time complexity of O(log n) by maintaining a balanced structure. This ensures that the tree does not become imbalanced like a red black tree could which would slow down operations significantly. Though a red black tree could be more effective for adding new data, we choose the AVL to prioritize getting the data as the dataset is much less likely to change compared to the rate data will be retrived by a user. Additionally, the ordered nature of the AVL tree allows us to efficiently and quickly search for the data, which rely on it being sorted.

## Why These Data Structures and Algorithms Are Effective and Efficient

The AVL tree's self-balancing property ensures that all operations remain efficient even as the database grows. This is especially important for maintaining high performance in a dynamic set where elements are frequently added or removed. Moreover, the algorithms used for balancing and searching through the AVL tree support efficient implementation of complex operations like finding related sayings or specific words, making it an ideal choice for our database.

